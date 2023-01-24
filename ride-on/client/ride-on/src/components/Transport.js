import React from "react";
import { useNavigate } from "react-router-dom";

function Transport({ currentUser, cars, setCars }) {

  const navigate = useNavigate();

  return (
    <>
      <div className="row text-center bubble-box text slide-right">
        <div className="col-12">
          <h4 className="mb-5">Your pick of rides at low prices</h4>
        </div>

        <div className="col-md-6 col-xl-4 offset-xl-2 mb-5">
          <img src="images/car-solid.svg" alt="Drive"
            className="drive-img"
            onClick={() => {
              // navigate to /signin page if user doesn't have an account
              if (!currentUser) {
                navigate("/signin");
              }
              // navigate to ../trip/form if user owns a car
              console.log(currentUser.cars.length); // currentCar.userId
              if (currentUser.cars.length > 0) {
                navigate("/api/ride_on/trip/form");
              } else {
                navigate("/api/ride_on/car/form");
              }
            }} />
          <p
            className="drive-text text"
            onClick={() => {
              navigate("/api/ride_on/trip")
            }}
          >Post a Ride?</p>
        </div>

        <div className="col-md-6 col-xl-4 mb-5">
          <img src="images/chair-solid.svg" alt="Ride"
            className="ride-img"
            onClick={() => {
              navigate("/api/ride_on/trip")
            }}
          />

          <p
            className="ride-text text"
            onClick={() => {
              navigate("/api/ride_on/trip")
            }}
          >Book a Ride!</p>
        </div>
      </div>

      {/* <div className="d-none d-md-block">
        <div className="row text-center">
          <div className="col-3 offset-3">
            <p className="drive-text">Post a Ride?</p>
          </div>

          <div className="col-3">
            <p className="ride-text">Book a Ride!</p>
          </div>
        </div>
      </div> */}
    </>
  );
}

export default Transport;
