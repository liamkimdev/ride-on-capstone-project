import React from "react";
import { useNavigate } from "react-router-dom";

function Home({ currentUser, cars, setCars }) {

  const navigate = useNavigate();

  return (
    <>
      <div className="d-flex align-items-center justify-content-center mx-auto">

        <div className="col-3">
          <button
            className="btn btn-secondary"
            onClick={() => {
              // navigate to /login page if user doesn't have an account
              if (!currentUser) {
                navigate("/login");
              }
              // navigate to ../trip/form if user owns a car
              console.log(currentUser); // currentCar.userId
              if (currentUser.cars) {
                navigate("/api/ride_on/trip/form");
              } else {
                navigate("/api/ride_on/car/form");
              }
            }}
          >Drive</button>
        </div>

        <div>
          <button
            className="btn btn-secondary"
            onClick={() => navigate("/api/ride_on/trip")}

          >Ride</button>
        </div>

      </div>
    </>
  );
}

export default Home;
