using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace WebServiceClient
{
    /// <summary>
    /// Interaction logic for ConfrimActionWithPassword.xaml
    /// </summary>
    public partial class ConfrimActionWithPassword : Window
    {
        public string userName;
        public string password;
        public bool confirmed;

        public ConfrimActionWithPassword(string confirmActionLabel, string confirmationButton)
        {
            InitializeComponent();

            confirmed = false;
            password = "";
            userName = "";
            ConfirmActionLabel.Content = confirmActionLabel;
            ConfirmationButton.Content = confirmationButton;
        }


        private void Cancel_Button_Click(object sender, RoutedEventArgs e)
        {
            confirmed = false;

            this.Close();
        }

        private void ConfirmationButton_Click(object sender, RoutedEventArgs e)
        {
            password = PasswordBox.Password.ToString();
            userName = UserBox.Text;
            confirmed = true;

            this.Close();
        }
    }
}
