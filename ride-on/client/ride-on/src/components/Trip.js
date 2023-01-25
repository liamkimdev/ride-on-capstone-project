import { useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import AuthContext from "../contexts/AuthContext";

function Trip({ trip, currentUser, cars, trips }) {
  const auth = useContext(AuthContext);

  const navigate = useNavigate();

  // useEffect(() => {
  //   ({
  //     departure: '',
  //     arrival: '',
  //     seats: '',
  //     pricePerSeat: '',
  //     date: '',
  //     riders: []
  //   });
  // }, [window.location.pathname]);

  const updateTrip = (trip) => {

    console.log(trips);
    console.log(trip.tripId);
    console.log(currentUser.username);

    fetch("http://localhost:8080/api/ride_on/trip/" + trip.tripId + "/" + currentUser.username, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + auth.currentUser.token
      },
      body: JSON.stringify(trip)
    })
      .then((response) => {
        if (response.status === 204) {
          alert("Successfully updated!")
        } else {
          alert("Something went wrong, unable to update");
        }
      })
      .catch(error => console.log(error));
  }

  const deleteTrip = () => {
    fetch("http://localhost:8080/api/ride_on/trip/" + trip.tripId, {
      method: "DELETE",
      headers: {
        Authorization: "Bearer " + auth.currentUser.token
      }
    })
      .then((response) => {
        if (response.status === 204) {
          alert("Successfully delete!")
        } else {
          alert("Something went wrong, unable to delete");
        }
      })
      .catch(error => console.log(error));
  }

  return (
    <>
      <tr>
        <td>{trip.departure}</td>
        <td>{trip.arrival}</td>
        <td>{trip.seats}</td>
        <td>${trip.pricePerSeat}.00</td>
        <td>{trip.date}</td>
        {auth.currentUser && auth.currentUser.hasRole("USER") ? (
          currentUser.cars.length > 0 && trips.map(trip => trip.carId === currentUser.cars.carId) ?
            //cars.some(car => car.carId === trip.carId && car.userId === currentUser.userId) ?       
            (
              <td>
                {/* Cancel for Driver View/ */}
                <button
                  className="btn"
                  style={{ color: "#FFFFFF", backgroundColor: "#FF4571" }}
                  onClick={() => {
                    deleteTrip();
                    navigate("/api/ride_on/trip");
                  }}
                >
                  Cancel Trip
                </button>
              </td>
            ) : (
              <td>
                {/* join for rider view */}
                <button
                  className="btn"
                  style={{ color: "#FFFFFF", backgroundColor: "#3CB2FB" }}
                  onClick={() => {
                    updateTrip();
                    navigate("/api/ride_on/trip/" + trip.tripId);
                  }}
                >
                  Join Trip
                </button>
              </td>
            )
        ) : null}

      </tr>
    </>
  );
}

export default Trip;
