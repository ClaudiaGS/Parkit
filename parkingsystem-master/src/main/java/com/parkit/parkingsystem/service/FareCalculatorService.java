package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

/**
 * Defines the calculator that returns the fare a vehicle needs to pay
 */
public class FareCalculatorService {
    /**
     * The method calculates the fare for the given ticket and associates it to the ticket
     *
     * @param ticket stores the parking information for a vehicle
     */
    public void calculateFare(Ticket ticket) {
        
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());
        }
        
        double inHour = ticket.getInTime().getTime();
        double outHour = ticket.getOutTime().getTime();
        double duration = ((outHour - inHour) / 1000 / 60);
  
        if (duration <= 30) {
            ticket.setPrice(0);
        } else {
            switch (ticket.getParkingSpot().getParkingType()) {
                case CAR: {
                    ticket.setPrice(duration * (Fare.CAR_RATE_PER_HOUR) / 60);
                    break;
                }
                case BIKE: {
                    ticket.setPrice(duration * (Fare.BIKE_RATE_PER_HOUR) / 60);
                    break;
                }
                default:
                    throw new IllegalArgumentException("Unkown Parking Type");
            }
            
            if (ticket.getRecurringVehicle()) {
                ticket.setPrice(ticket.getPrice() * 0.95);
            }
        }
    }
}
