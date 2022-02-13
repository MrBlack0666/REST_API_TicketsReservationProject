using System;
using System.Collections.ObjectModel;
using System.ServiceModel;
using System.Windows;
using System.Windows.Controls;
using WebServiceClient.Server;
using WebServiceClient.Server.Models;

namespace WebServiceClient
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private ServiceResources service;

        private ObservableCollection<Flight> flights;
        
        private Flight selectedFlight;

        public MainWindow()
        {
            InitializeComponent();

            var binding = new BasicHttpBinding();
            binding.MessageEncoding = WSMessageEncoding.Mtom;

            service = ServiceResources.GetInstance();

            this.DataContext = this;
            this.selectedFlight = null;
            

            FlightDetailsGrid.Visibility = Visibility.Hidden;

            GetFlights();
        }

        private void GetFlights()
        {

            var flightsList = service.GetFlights();
            flights = new ObservableCollection<Flight>(flightsList);
            
            FlightsListBox.ItemsSource = flights;

        }

        private void AddFlightButton_Click(object sender, RoutedEventArgs e)
        {
            AddEditFlightWindow addEditFlightWindow = new AddEditFlightWindow(null, service);
            addEditFlightWindow.ShowDialog();
            if (addEditFlightWindow.flight != null)
            {
                flights.Add(addEditFlightWindow.flight);
            }
        }

        private void EditFlightButton_Click(object sender, RoutedEventArgs e)
        {
            AddEditFlightWindow addEditFlightWindow = new AddEditFlightWindow(this.selectedFlight, service);
            addEditFlightWindow.ShowDialog();
            if (addEditFlightWindow.flight == null && addEditFlightWindow.isEditing == true)
            {
                GetFlights();
            }
        }

        private void FlightsListBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {

            this.selectedFlight = (Flight)((ListBox)sender).SelectedItem;
            if(selectedFlight != null)
            {
                FlightFromLabel.Content = selectedFlight.From_City;
                FlightToLabel.Content = selectedFlight.To_City;
                FlightArrivalDateLabel.Content = selectedFlight.FlightArrivalDateString;
                FlightDepartureDateLabel.Content = selectedFlight.FlightDepartureDateString;
                PriceLabel.Content = selectedFlight.Price + " zł";
                NumberOfAvaiableSeatsLabel.Content = selectedFlight.NumberOfAvaiableSeats;
                NumberOfSeatsLabel.Content = selectedFlight.NumberOfSeats;

                this.FlightDetailsGrid.Visibility = Visibility.Visible;
            }
            else
            {
                this.FlightDetailsGrid.Visibility = Visibility.Hidden;
            }
        }

        private void DeleteFlightButton_Click(object sender, RoutedEventArgs e)
        {
            ConfrimActionWithPassword confirmActionWithPassword = new ConfrimActionWithPassword("Czy na pewno chcesz usunąć lot?", "Usuń");
            confirmActionWithPassword.ShowDialog();
            if (confirmActionWithPassword.confirmed)
            {
                User user = new User() { UserName = confirmActionWithPassword.userName, Password = confirmActionWithPassword.password };
                bool isDeleted = service.DeleteFlight(selectedFlight.Id, user);

                if(!isDeleted)
                {
                    MessageBoxResult messageBoxResult = MessageBox.Show("Nie podano poprawnie użytkownika lub hasła", "Błąd autoryzacji", MessageBoxButton.OK);
                    return;
                }

                selectedFlight = null;
                GetFlights();
            }
        }

        private void ReservationButton_Click(object sender, RoutedEventArgs e)
        {
            AddReservationWindow addReservationWindow = new AddReservationWindow(selectedFlight, service);
            addReservationWindow.ShowDialog();
            if (addReservationWindow != null)
            {
                GetFlights();
            }
        }

        private void SearchReservationButton_Click(object sender, RoutedEventArgs e)
        {
            if (string.IsNullOrWhiteSpace(SearchReservationTextBox.Text))
            {
                return;
            }

            var reservation = service.GetReservationById(int.Parse(SearchReservationTextBox.Text));

            if (reservation != null)
            {
                DisplayReservationDetailsWindow displayReservationDetailsWindow = new DisplayReservationDetailsWindow(reservation, service);
                displayReservationDetailsWindow.ShowDialog();
                if (displayReservationDetailsWindow.reservation == null)
                {
                    GetFlights();
                }
            }
            else
            {
                MessageBoxResult messageBoxResult = MessageBox.Show("Nie znaleziono rezerwacji", "Błąd wyszukiwania", MessageBoxButton.OK);
            }
        }
    }
}
