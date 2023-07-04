package com.driver.services;

import com.driver.Repository.HotelManagementRepository;
import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
//import org.springframework.web.bind.annotation.Service;

@Service
public class HotelManagementService {
    @Autowired
    HotelManagementRepository hotelManagementRepository;
    public String addHotel(Hotel hotel) {
        if(hotel == null)
            return "FAILURE";
        if(hotel.getHotelName() == null)
            return "FAILURE";

        return hotelManagementRepository.addHotel(hotel);
    }

    public Integer addUser(User user) {
        return hotelManagementRepository.addUser(user);
    }

    public String getHotelWithMostFacilities() {
        return hotelManagementRepository.getHotelWithMostFacilities();
    }

    public int bookARoom(Booking booking) {

        return hotelManagementRepository.bookARoom(booking);
    }

    public int getBooking(Integer aadharCard) {
        return hotelManagementRepository.getBooking(aadharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        return hotelManagementRepository.updateFacilities(newFacilities,hotelName);
    }
}
