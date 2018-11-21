/*
  IMPORTANT: Make sure you are using the correct package name.
  This example uses the package name:
  package com.example.android.justjava
  If you get an error when copying this code into Android studio, update it to match teh package name found
  in the project's AndroidManifest.xml file.
 */
package com.example.jcesa.justjavaapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method show a resume message
     *
     * @param price           of the order
     * @param addWhippedCream is whether or not  the user wants whipped cream topping
     * @param hasChocolate    of the order
     * @param hasNameClient   name of client
     * @return text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean hasChocolate, String hasNameClient) {
        String priceMessage = getString(R.string.your_name) + " " + hasNameClient;
        priceMessage += "\n" + getString(R.string.add_whipped_cream) + " " + addWhippedCream;
        priceMessage += "\n" + getString(R.string.text_chocolate) + " " + hasChocolate;
        priceMessage += "\n" + getString(R.string.text_cantidad) + " " + quantity;
        priceMessage += "\nTotal: " + "$" + price;
        priceMessage += "\n" + getString(R.string.thank_you) + " ";
        return priceMessage;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        /* get name of client*/
        EditText getNameEditTExt = findViewById(R.id.client_name);
        String hasNameClient = getNameEditTExt.getText().toString();
        /* if user wants whipped Cream */
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        /* if user wants chocolate */
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasChocolate, hasWhippedCream);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, hasNameClient);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Check List");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @param hasChocolate    variable chocolate
     * @param hasWhippedCream variable crema batida
     * @return total price
     */
    private int calculatePrice(boolean hasChocolate, boolean hasWhippedCream) {
        int price = 5;

        if (hasWhippedCream) {
            price = price + 1;
        }
        if (hasChocolate) {
            price = price + 2;
        }
        return quantity * price;
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            //Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffee", Toast.LENGTH_SHORT).show();
            //Exit this method early.
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            //Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            //Exit this method early.
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(number));
    }

//    /**
//     * This method displays the given price on the screen.
//     *
//     * @param message this text is the price of coffees.
//     */
////    private void displayMessage(String message) {
//        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}
