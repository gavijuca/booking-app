package io.muserver.sample.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class BookingDTO implements Serializable {

    private String id;
    private String customerName;
    private int tableSize;
    private String reservedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getTableSize() {
        return tableSize;
    }

    public void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }

    public String getReservedAt() {
        return reservedAt;
    }

    public void setReservedAt(String reservedAt) {
        this.reservedAt = reservedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingDTO that = (BookingDTO) o;
        return tableSize == that.tableSize && id.equals(that.id) && Objects.equals(customerName, that.customerName) && Objects.equals(reservedAt, that.reservedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerName, tableSize, reservedAt);
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
            "id='" + id + '\'' +
            ", customerName='" + customerName + '\'' +
            ", tableSize=" + tableSize +
            ", reservedAt='" + reservedAt + '\'' +
            '}';
    }
}
