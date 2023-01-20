import React from "react";
import { useNavigate } from "react-router-dom";

function Home() {

  const navigate = useNavigate();

  return (
    <>
      <div>
        <button 
          className="btn btn-secondary"
          onClick={() => navigate("/api/ride_on/trip/form")}
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
