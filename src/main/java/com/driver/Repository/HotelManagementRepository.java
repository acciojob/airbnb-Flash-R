package com.driver.Repository;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class HotelManagementRepository {

    HashMap<String, Hotel> hotels = new HashMap<>();
    HashMap<Integer, User> users = new HashMap<>();
    HashMap<String, Booking> bookings = new HashMap<>();
    public String addHotel(Hotel hotel) {
        if(hotels.containsKey(hotel.getHotelName()))
            return "FAILURE";
        hotels.put(hotel.getHotelName(), hotel);
        return "SUCCESS";
    }

    public Integer addUser(User user) {
        users.put(user.getaadharCardNo(),user);
        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities() {
        String maxfacility = "";
        int max = 0;
        for(String h : hotels.keySet()){
            int facility = hotels.get(h).getFacilities().size();
            if(facility > max){
                maxfacility = h;
                max = facility;
            }

        }
        if(max == 0)
            return "";
        return maxfacility;
    }

    public int bookARoom(String id, Booking booking) {

        if(hotels.get(booking.getHotelName()).getAvailableRooms() < booking.getNoOfRooms()){
            return -1;
        }
        int pricePerNight = hotels.get(booking.getHotelName()).getPricePerNight();
        int numberOfRooms = booking.getNoOfRooms();

        int price = pricePerNight * numberOfRooms;
        booking.setAmountToBePaid(price);

        bookings.put(id,booking);
        hotels.put(hotels.get(booking.getHotelName()).getHotelName(),hotels.get(booking.getHotelName()));
        return price;
    }

    public int getBooking(Integer aadharCard) {
        int count = 0;

        for(String id : bookings.keySet()){
            int personFromDB = bookings.get(id).getBookingAadharCard();
            if(personFromDB == aadharCard)
                count++;
        }
        return count;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel hotel = hotels.get(hotelName);
        List<Facility> oldfacilities = hotel.getFacilities();

        for(Facility facility : newFacilities ){
            if(oldfacilities.contains(facility)){
                continue;
            }
            else{
                oldfacilities.add(facility);
            }
        }
        hotel.setFacilities(oldfacilities);
        hotels.put(hotelName,hotel);

        return hotel;

    }
}
