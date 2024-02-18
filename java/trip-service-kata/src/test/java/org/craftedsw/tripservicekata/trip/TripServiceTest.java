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

        loggedinUser = REGISTERD_USER;

        User friend = new User();
        friend.addFriend(ANOTHER_USER);
        friend.addTrip(TO_BRAZIL);

        List<Trip> friendTrips = tripService.getTripsByUser(friend);

        assertEquals( friendTrips.size() , 0 );
    }


    @Test public void
    should_return_friend_trip_when_users_are_friends(){
        loggedinUser = REGISTERD_USER;

        User friend = new User();
        friend.addFriend(ANOTHER_USER);
        friend.addFriend(loggedinUser);
        friend.addTrip(TO_BRAZIL);
        friend.addTrip(TO_LONDON);

        List<Trip> friendTrips = tripService.getTripsByUser(friend);

        assertEquals( friendTrips.size() , 2 );
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
