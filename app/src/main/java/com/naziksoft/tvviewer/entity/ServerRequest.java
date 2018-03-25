package com.naziksoft.tvviewer.entity;

/**
 * Created by nazar on 25.03.18.
 */

public class ServerRequest {
    private String serialNumber;
    private long borderId;
    private int direction;

    public ServerRequest(String serialNumber, long borderId, int direction) {
        this.serialNumber = serialNumber;
        this.borderId = borderId;
        this.direction = direction;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public long getBorderId() {
        return borderId;
    }

    public int getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServerRequest request = (ServerRequest) o;

        if (borderId != request.borderId) return false;
        if (direction != request.direction) return false;
        return serialNumber != null ? serialNumber.equals(request.serialNumber) : request.serialNumber == null;
    }

    @Override
    public int hashCode() {
        int result = serialNumber != null ? serialNumber.hashCode() : 0;
        result = 31 * result + (int) (borderId ^ (borderId >>> 32));
        result = 31 * result + direction;
        return result;
    }

    @Override
    public String toString() {
        return "ServerRequest{" +
                "serialNumber='" + serialNumber + '\'' +
                ", borderId='" + borderId + '\'' +
                ", direction=" + direction +
                '}';
    }
}
