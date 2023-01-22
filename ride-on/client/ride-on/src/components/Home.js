import React from "react";
import { useNavigate } from "react-router-dom";

function Home(currentUser) {

  const navigate = useNavigate();



  return (
    <>
      <div>
        <button 
          className="btn btn-secondary"
          onClick={ ()=> {
              navigate("/api/ride_on/car/form")
          }}

        >Drive</button>

        <button
          className="btn btn-secondary"
          onClick={() => navigate("/api/ride_on/trip")}
        
        >Ride</button>
      </div>
    </>
  );
}

export default Home;
