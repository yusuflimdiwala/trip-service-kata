package org.craftedsw.tripservicekata.trip;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

public class TripServiceTest {

    private static final User GUEST = null;
    private static final User UNUSED_USER = null;
    private static final User REGISTERD_USER = new User();
    private static final User ANOTHER_USER = new User();
    private static final Trip TO_BRAZIL = new Trip();
    private static final Trip TO_LONDON = new Trip() ;
    private User loggedinUser;
    private TripService tripService;

    @BeforeEach
    public void initialize(){

        tripService = new TestableTripService();
        loggedinUser = REGISTERD_USER;
    }

    @Test  public void
    should_throw_an_exception_when_user_is_not_logged_in(){


        assertThrows(UserNotLoggedInException.class, () -> {

            loggedinUser = GUEST;
            tripService.getTripsByUser(UNUSED_USER);
        } );

    }

    @Test public void
    should_not_return_any_trip_when_users_are_not_friends(){



        User friend = UserBuilder.auser()
                        .friendsWith(ANOTHER_USER)
                        .withTrips(TO_BRAZIL)
                        .build();


        List<Trip> friendTrips = tripService.getTripsByUser(friend);

        assertEquals( friendTrips.size() , 0 );
    }


    @Test public void
    should_return_friend_trip_when_users_are_friends(){

        User friend = UserBuilder.auser()
                        .friendsWith(ANOTHER_USER, loggedinUser)
                        .withTrips(TO_BRAZIL,TO_LONDON)
                        .build();





        List<Trip> friendTrips = tripService.getTripsByUser(friend);

        assertEquals( friendTrips.size() , 2 );
    }

    public static class UserBuilder {

        private User[] friends = new User[] {};
        private Trip[] trips = new Trip[] {};

        public static UserBuilder auser() {
            return new UserBuilder();

        }
        public UserBuilder friendsWith(User... friends) {
            this.friends = friends;
            return this;
        }

        public UserBuilder withTrips(Trip... trips) {
            this.trips = trips;
            return this;
        }

        public User build() {
            User user = new User();
            addTripsTo(user);
            addFriendsTo(user);
            return user;
        }

        private void addFriendsTo(User user) {
            for(User friend : friends){
                user.addFriend(friend);
            }
        }

        private void addTripsTo(User user) {
            for(Trip trip : trips){
                user.addTrip(trip);
            }

        }
    }

    private class TestableTripService extends TripService{
        @Override
        protected User getLoggedUser() {
            return loggedinUser;
        }

        @Override
        public List<Trip> tripsBy(User user) throws UserNotLoggedInException {
            return  user.trips();
        }
    }
}
