using System;
using System.Collections.Generic;
using System.Linq;
using System.ServiceModel;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using WebServiceClient.Server;
using WebServiceClient.Server.Models;
using WebServiceClient.Server.DTOs;

namespace WebServiceClient
{
    /// <summary>
    /// Interaction logic for AddEditFlightWindow.xaml
    /// </summary>
    public partial class AddEditFlightWindow : Window
    {
        public Flight flight;
        public bool isEditing;

        private ServiceResources service;

        public AddEditFlightWindow(Flight flight, ServiceResources service)
        {
            InitializeComponent();

            this.service = service;
            this.flight = flight;
            this.isEditing = flight != null;

            if (isEditing)
            {
                AddOrEditFlightButton.Content = "Edytuj";

                FlightTimeTextBox.Text = Math.Floor((flight.FlightArrivalDate - flight.FlightDepartureDate).TotalMinutes).ToString();
                FlightDepartureDateDTP.Value = flight.FlightDepartureDate;
                FromCityTextBox.Text = flight.From_City;
                ToCityTextBox.Text = flight.To_City;
                NumberOfSeatsTextBox.Text = flight.NumberOfSeats.ToString();
                PriceTextBox.Text = flight.Price.ToString();
            }
        }

        private void NumberOfSeatsValidationTextBox(object sender, TextCompositionEventArgs e)
        {
            Regex regex = new Regex("[^0-9]+");
            e.Handled = regex.IsMatch(e.Text);
        }

        private void PriceValidationTextBox(object sender, TextCompositionEventArgs e)
        {

            var values = (((TextBox)sender).Text + e.Text).Split(new char[2] { '.', ',' });
            if (values.Length <= 0)
            {
                e.Handled = false;
                return;
            }

            if (values.Length > 2)
            {
                e.Handled = true;
                return;
            }

            if (!long.TryParse(values[0], out var value1))
            {
                e.Handled = true;
                return;
            }
            if (values.Length > 1 && values[1].Length > 0 && (values[1].Length > 2 || !int.TryParse(values[1], out var value2)))
            {
                e.Handled = true;
                return;
            }

            e.Handled = false;
        }

        private bool FlightDetailsValidation()
        {
            bool temp = true;

            ErrorFromCityLabel.Visibility = Visibility.Hidden;
            ErrorToCityLabel.Visibility = Visibility.Hidden;
            ErrorFlightDepartureDateLabel.Visibility = Visibility.Hidden;
            ErrorFlightTimeLabel.Visibility = Visibility.Hidden;
            ErrorPriceLabel.Visibility = Visibility.Hidden;
            ErrorNumberOfSeatsLabel.Visibility = Visibility.Hidden;

            if (string.IsNullOrWhiteSpace(FromCityTextBox.Text))
            {
                ErrorFromCityLabel.Visibility = Visibility.Visible;
                temp = false;
            }

            if (string.IsNullOrWhiteSpace(ToCityTextBox.Text))
            {
                ErrorToCityLabel.Visibility = Visibility.Visible;
                temp = false;
            }

            if (FlightDepartureDateDTP.Value == null)
            {
                ErrorFlightDepartureDateLabel.Visibility = Visibility.Visible;
                temp = false;
            }

            if (!int.TryParse(FlightTimeTextBox.Text, out var flightTime))
            {
                ErrorFlightTimeLabel.Visibility = Visibility.Visible;
                temp = false;
            }

            if (!decimal.TryParse(PriceTextBox.Text.Replace('.', ','), out var price))
            {
                ErrorPriceLabel.Visibility = Visibility.Visible;
                temp = false;
            }

            if (!int.TryParse(NumberOfSeatsTextBox.Text, out var numberOfSeats) || numberOfSeats <= 0)
            {
                ErrorNumberOfSeatsLabel.Visibility = Visibility.Visible;
                temp = false;
            }

            return temp;
        }

        private void AddOrEditFlightButton_Click(object sender, RoutedEventArgs e)
        {
            if (!FlightDetailsValidation())
            {
                return;
            }

            if (isEditing)
            {
                ConfrimActionWithPassword confirmActionWithPassword = new ConfrimActionWithPassword("Czy na pewno chcesz zedytować lot?", "Edytuj");
                confirmActionWithPassword.ShowDialog();
                if (confirmActionWithPassword.confirmed)
                {
                    User user = new User() { UserName = confirmActionWithPassword.userName, Password = confirmActionWithPassword.password };

                    var flightArrivalDate = ((DateTime)FlightDepartureDateDTP.Value).AddMinutes(int.Parse(FlightTimeTextBox.Text));

                    var wasEdited = service.EditFlight(new Flight()
                    {
                        FlightArrivalDate = flightArrivalDate,
                        FlightDepartureDate = (DateTime)FlightDepartureDateDTP.Value,
                        From_City = FromCityTextBox.Text,
                        To_City = ToCityTextBox.Text,
                        NumberOfSeats = int.Parse(NumberOfSeatsTextBox.Text),
                        Id = flight.Id,
                        Price = decimal.Parse(PriceTextBox.Text.Replace('.', ','))
                    }, user);

                    if (wasEdited)
                    {
                        flight = null;
                    }
                    else
                    {
                        MessageBoxResult messageBoxResult = MessageBox.Show("Nie podano poprawnie użytkownika lub hasła", "Błąd autoryzacji", MessageBoxButton.OK);
                        return;
                    }
                }
            }
            else
            {
                ConfrimActionWithPassword confirmActionWithPassword = new ConfrimActionWithPassword("Czy na pewno chcesz dodać lot?", "Dodaj");
                confirmActionWithPassword.ShowDialog();
                if (confirmActionWithPassword.confirmed)
                {
                    User user = new User() { UserName = confirmActionWithPassword.userName, Password = confirmActionWithPassword.password };
                    var flightArrivalDate = ((DateTime)FlightDepartureDateDTP.Value).AddMinutes(int.Parse(FlightTimeTextBox.Text));

                    flight = service.AddFlight(new Flight
                    {
                        FlightArrivalDate = flightArrivalDate,
                        FlightDepartureDate = (DateTime)FlightDepartureDateDTP.Value,
                        From_City = FromCityTextBox.Text,
                        To_City = ToCityTextBox.Text,
                        NumberOfSeats = int.Parse(NumberOfSeatsTextBox.Text),
                        Price = decimal.Parse(PriceTextBox.Text.Replace('.', ','))
                    }, user);

                    if (flight == null)
                    {

                        MessageBoxResult messageBoxResult = MessageBox.Show("Nie podano poprawnie użytkownika lub hasła", "Błąd autoryzacji", MessageBoxButton.OK);
                        return;
                    }
                }
            }

            this.Close();
        }

        private void Cancel_Button_Click(object sender, RoutedEventArgs e)
        {
            flight = null;
            this.Close();
        }
    }
}
