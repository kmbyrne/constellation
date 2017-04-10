package com.mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.ArrayList;
import java.util.List;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class MySampleApplication implements EntryPoint
{
	private VerticalPanel mainPanel = new VerticalPanel();
	private FlexTable stocksFlexTable = new FlexTable();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private TextBox newSymbolTextBox = new TextBox();
	private Button addStockButton = new Button("Add");
	private Label lastUpdatedLabel = new Label();
	private List<String> stocks = new ArrayList<>();

	/**
	 * Entry point method.
	 */
	public void onModuleLoad()
	{
		// Create table for stock data.
		stocksFlexTable.setText(0, 0, "Symbol");
		stocksFlexTable.setText(0, 1, "Price");
		stocksFlexTable.setText(0, 2, "Change");
		stocksFlexTable.setText(0, 3, "Remove");

		// Assemble Add Stock panel.
		addPanel.add(newSymbolTextBox);
		addPanel.add(addStockButton);

		// Assemble Main panel.
		mainPanel.add(stocksFlexTable);
		mainPanel.add(addPanel);
		mainPanel.add(lastUpdatedLabel);

		// Associate the Main panel with the HTML host page.
		RootPanel.get("stockList").add(mainPanel);
		// Move cursor focus to the input box.
		newSymbolTextBox.setFocus(true);

		addStockButton.addClickHandler(event -> addStock());

		newSymbolTextBox.addKeyDownHandler(event ->
										   {
											   if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER)
											   {
												   addStock();
											   }
										   });
	}

	private void addStock()
	{
		final String symbol = newSymbolTextBox.getText().toUpperCase().trim();
		newSymbolTextBox.setFocus(true);

		if (!symbol.matches("^[0-9A-Z\\.]{1,10}$"))
		{
			Window.alert("'" + symbol + "' is not a valid symbol.");
			newSymbolTextBox.selectAll();
			return;
		}

		if(stocks.contains(symbol)) return;

		int row = stocksFlexTable.getRowCount();
		stocks.add(symbol);
		stocksFlexTable.setText(row, 0, symbol);

		Button removeStockButton = new Button("x");
		removeStockButton.addClickHandler(event -> {
			int removedIndex = stocks.indexOf(symbol);
			stocks.remove(removedIndex);
			stocksFlexTable.removeRow(removedIndex + 1);
		});
		stocksFlexTable.setWidget(row, 3, removeStockButton);

		newSymbolTextBox.setText("");
	}
}
