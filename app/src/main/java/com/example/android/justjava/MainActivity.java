/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

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

    private String createOrderSummary(int price, boolean addWippedCream, boolean addChocolate, String name) {
        String priceMessage = "Name: " + name;
        priceMessage = priceMessage + "\nAdd Wipped Cream? " + addWippedCream;
        priceMessage = priceMessage + "\nAdd Chocolate? " + addChocolate;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage = priceMessage + "\nTotal: $" + price;
        priceMessage = priceMessage + "\nThank you!";
        return  priceMessage;
    }

    /**
     * This method displays the given price on the screen.
     */


    private int calculatePrice(boolean hasWippedCream, boolean hasChocolate){
        int multiplier = 5;

        if (hasWippedCream) multiplier += 1;
        if (hasChocolate) multiplier += 2;


        int price = quantity * multiplier;
        return price;
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.wipped_cream_checkbox);
        boolean hasWippedCream = checkBox.isChecked();

        CheckBox checkBox1 = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = checkBox1.isChecked();

        int price = calculatePrice(hasWippedCream, hasChocolate);
        EditText editText = (EditText) findViewById(R.id.name_field);
        String name = editText.getText().toString();
        String priceMessage = createOrderSummary(price, hasWippedCream, hasChocolate, name);



            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"manish3499@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order for: " + name );
            intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }


    }

    public void increment(View view){
        if (quantity == 100) {
            Toast.makeText(this, "You cannot order more than 100 cups", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement (View view) {
        if (quantity == 1) {
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.quantity_text_view);
        orderSummaryTextView.setText("" + number);
    }
}