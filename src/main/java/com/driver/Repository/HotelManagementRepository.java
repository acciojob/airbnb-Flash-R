package com.driver.Repository;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HotelManagementRepository {

    HashMap<String, Hotel> hotels = new HashMap<>();
    HashMap<Integer, User> users = new HashMap<>();
    HashMap<String, Booking> bookings = new HashMap<>();
    HashMap<Integer, List<Booking>> personBookingsList = new HashMap<>();
    public String addHotel(Hotel hotel) {
        if(hotels.containsKey(hotel.getHotelName()) || hotel == null || hotel.getHotelName() == null){
            return "FAILURE";
        }else
            hotels.put(hotel.getHotelName(), hotel);
        return "SUCCESS";
    }

    public Integer addUser(User user) {
        if(user == null) return -1;
        users.put(user.getaadharCardNo(),user);
        return user.getaadharCardNo();
    }

    public String getHotelWithMostFacilities() {
        String maxfacility = "";
        int max = -1;
        for(String hotelName : hotels.keySet()){
            Hotel hotel = hotels.get(hotelName);
            if(hotel.getFacilities().size() > max){
                maxfacility = hotelName;
                max = hotel.getFacilities().size();
            }
            else if(hotel.getFacilities().size() == max){
                String[] arr = new String[] {hotelName, maxfacility};
                Arrays.sort(arr);
                maxfacility = arr[0];
            }
        }
        return max <= 0 ? "" : maxfacility;
    }

    public int bookARoom(Booking booking) {
        if(booking == null) return -1;
        String id = String.valueOf(UUID.randomUUID());
        booking.setBookingId(id);

        String hotelName = booking.getHotelName();
        Hotel hotel = hotels.get(hotelName);
        int pricePerNight = hotel.getPricePerNight();
        int noOfRooms = booking.getNoOfRooms();
        int availableRooms = hotel.getAvailableRooms();

        if(noOfRooms > availableRooms){
            return -1;
        }
        int amountPaid = noOfRooms * pricePerNight;
        booking.setAmountToBePaid(amountPaid);

        hotel.setAvailableRooms(availableRooms - noOfRooms);
        bookings.put(id, booking);
        int aadharNumber = booking.getBookingAadharCard();
        if(!personBookingsList.containsKey(aadharNumber)) {
            personBookingsList.put(aadharNumber, new ArrayList<>());
        }

        personBookingsList.get(aadharNumber).add(booking);

        return amountPaid;
    }

    public int getBooking(Integer aadharCard) {
        if(!personBookingsList.containsKey(aadharCard)) return 0;
        return personBookingsList.get(aadharCard).size();
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel hotel = hotels.get(hotelName);
        List<Facility> oldfacilities = hotel.getFacilities();

        for(Facility facility : newFacilities ){
            if(!oldfacilities.contains(facility)){
                oldfacilities.add(facility);
            }
        }
        hotel.setFacilities(oldfacilities);

        return hotel;

    }
}
