import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthContext from "../contexts/AuthContext";

function Trip({ trip, updateTrip, deleteTrips }) {
  const auth = useContext(AuthContext);

  const navigate = useNavigate();

  const addRiderToTrip = () => {

    const rider = {

      totalCost: trip.pricePerSeat,
      paymentConfirmation: true,
      userId: auth.currentUser.userId,
      tripId: trip.tripId

    }

    fetch("http://localhost:8080/api/ride_on/rider/" + auth.currentUser.userId + "/" + trip.tripId, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + auth.currentUser.token
      },
      body: JSON.stringify(rider)
    })
      .then((response) => {
        if (response.status === 201) {
          return response.json();
        } else {
          alert("Something went wrong, unable to update");
        }
      })
      .then((data) => {
        if (data) {
          console.log(data);
          updateTrip(trip.tripId, data);
          //setRiders(riders => [...riders, data])
        }
      })
      .catch(error => console.log(error));
  }

  // const updateTrip = () => {

  //   console.log(trip);

  //   fetch("http://localhost:8080/api/ride_on/trip/" + trip.tripId + "/" + currentUser.username, {
  //     method: "PUT",
  //     headers: {
  //       "Content-Type": "application/json",
  //       Authorization: "Bearer " + auth.currentUser.token
  //     },
  //     body: JSON.stringify(trip)
  //   })
  //     .then((response) => {
  //       if (response.status === 204) {
  //         alert("Successfully updated!")
  //       } else {
  //         alert("Something went wrong, unable to update");
  //       }
  //     })
  //     .catch(error => console.log(error));
  // }

  const deleteTrip = () => {
    fetch("http://localhost:8080/api/ride_on/trip/" + trip.tripId, {
      method: "DELETE",
      headers: {
        Authorization: "Bearer " + auth.currentUser.token
      }
    })
      .then((response) => {
        if (response.status === 204) { 
          deleteTrips(trip.tripId);
        } else {
          alert("Something went wrong, unable to delete");
        }
      })
      .catch(error => console.log(error));
  }

  const tripBelongsToUser = auth.currentUser && !!auth.currentUser.cars.find(car => car.carId == trip.carId);

  const userOnTrip = auth.currentUser && !tripBelongsToUser && !trip.riders.find(rider => rider.userId == auth.currentUser?.userId);

  console.log(trip);
  console.log(tripBelongsToUser);

  return (
    <>
      <tr>
        <td>{trip.departure}</td>
        <td>{trip.arrival}</td>
        <td>{trip.seats - trip.riders.length}</td>
        <td>${trip.pricePerSeat}.00</td>
        <td>{trip.date}</td>
        {userOnTrip &&
          (
            <td>
              <button
                className="btn"
                style={{ color: "#FFFFFF", backgroundColor: "#3CB2FB" }}
                onClick={ addRiderToTrip }
              >
                Join Trip
              </button>
            </td>
          )}
        {tripBelongsToUser &&
          (
            <td>
              <button
                className="btn"
                style={{ color: "#FFFFFF", backgroundColor: "#FF4571" }}
                onClick={() => {
                  deleteTrip();
                }}
              >
                Cancel Trip
              </button>
            </td>
          )}
      </tr>
    </>
  );
}

export default Trip;
