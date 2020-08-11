import javax.swing.table.AbstractTableModel;

public class TableData extends AbstractTableModel{

	@Override
	public int getRowCount() {
		return PurchaseTheCAT.Row_num();
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return PurchaseTheCAT.Table_data(rowIndex, columnIndex);
	}
	
	@Override
	public String getColumnName(int columnIndex) {
		return PurchaseTheCAT.Headings(columnIndex);
	};
	
}