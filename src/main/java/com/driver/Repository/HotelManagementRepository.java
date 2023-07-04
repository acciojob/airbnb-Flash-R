package com.driver.Repository;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Repository
public class HotelManagementRepository {

    HashMap<String, Hotel> hotels = new HashMap<>();
    HashMap<Integer, User> users = new HashMap<>();
    HashMap<String, Booking> bookings = new HashMap<>();
    public String addHotel(Hotel hotel) {
        if(hotels.containsKey(hotel.getHotelName())){
            return "FAILURE";
        }else
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
        for(String hotelName : hotels.keySet()){
            Hotel hotel = hotels.get(hotelName);
            if(hotel.getFacilities().size() > max){
                maxfacility = hotelName;
                max = hotel.getFacilities().size();
            }
            else if(hotel.getFacilities().size() == max){
                if(hotelName.compareTo(maxfacility) < 0){
                    maxfacility = hotelName;
                }
            }
        }
        return maxfacility;
    }

    public int bookARoom(Booking booking) {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
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
        hotels.put(hotelName, hotel);
        return amountPaid;
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
