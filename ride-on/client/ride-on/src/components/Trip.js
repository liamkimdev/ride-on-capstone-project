import { useContext } from "react";
import { useNavigate } from "react-router-dom";
import AuthContext from "../contexts/AuthContext";

function Trip({ trip, currentUser, cars, trips }) {
  const auth = useContext(AuthContext);

  const navigate = useNavigate();

  return (
    <>
    <tr>
      <td>{trip.departure}</td>
      <td>{trip.arrival}</td>
      <td>{trip.seats}</td>
      <td>{trip.pricePerSeat}</td>
      <td>{trip.date}</td>
      {auth.currentUser && auth.currentUser.hasRole("USER") ? (
        currentUser.cars.length > 0 && trips.map(trip => trip.carId === currentUser.cars.carId) ?
         (
          <td>
            {/* Cancel for Driver View/ */}
            <button
              className="btn btn-danger ms-2"
              onClick={() => 
                //cancel the trip
                
                navigate("/api/ride_on/trip")}
            >
              Cancel
            </button>
          </td>
        ) : (
          <td>
            {/* join for rider view */}
            <button
              className="btn btn-secondary"
              onClick={() => 
                //trip seat number decrease by one
                //add user to the trip
                navigate("/api/ride_on/trip/" + trip.tripId)}
            >
              Join
            </button>
          </td>
        )
      ) : null}
      
    </tr>
    </>
  );
}

export default Trip;
