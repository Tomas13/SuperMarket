package kazpost.kz.supermarket.data.network.model;

/**
 * Created by root on 5/15/17.
 */

public class SendData {
    String barcode, row, cell, index, access ="939ae3ec-a906-487c-a5d5-dabd0c3a52c3";



    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
