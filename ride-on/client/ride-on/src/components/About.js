

function About() {
  return (
    <div className="container mt-5 pt-5">
    <div className="text slide-right">
      <div
      style={{ fontFamily: "cutestThings", fontSize: "50px", color: "#4A7BDE", textShadow: "2px 2px 2px #FFFFFF" }}>
        <h1 className="text-center"><strong>What is Ride On?</strong></h1>
      </div>
      <br></br>
      <div className="bubble-box">
        <p className="text-center">
          <strong>What is the primary goal of this application?</strong>
        </p>

        <p>
          Aimed to instill faith and trust in community by connecting drivers
          of everyday trips with passengers looking for a ride to the same
          destination. All while saving money and resources, reducing traffic
          congestion, limiting enviromental impacts and fostering friendships
          along the way.
        </p>

        <p>
          In the future, we also aim to connect employees within a company and
          students within a university.
        </p>
      </div>
      <br></br>
      <div className="bubble-box">
        <p className="text-center">
          <strong>What are the features of this application?</strong>
        </p>

        <p>
          “Ride On” is an online marketplace for carpooling. Its website and
          mobile apps connect drivers and passengers willing to travel
          together between cities and share the cost of journey. The company
          does not own any vehicle; This application makes it fun and
          affordable to carpool from city-to-city in the United States of
          America. Save money, reduce your carbon footprint, and make new
          friends.
        </p>
      </div>
      {/* <br></br>
      <div className="bubble-box">
        <p className="text-center">
          <strong>How is it different from Uber and Lyft?</strong>
        </p>

        <p>
          Uber and Lyft focus on an on-demand type of business model where you
          request a ride and someone with a car will accommodate to your
          desires, in other words the passenger is the one who runs the show
          and the driver follows along. With Ride on, the driver is the one
          making the decision on where to go and the passenger tags along.
        </p>

        <p>And most importantly, much cheaper than Uber and Lyft!</p>
      </div> */}
      <br></br>
      <div className="bubble-box">
        <p className="text-center">
          <strong>Safety concerns?</strong>
        </p>

        <p className="text-center">Safety is our number one priority</p>

        <p>
          We go the extra mile to ensure members have a safe experience on
          “Ride On”, such as, verifying users via identifciation, choose who
          you travel with, admin monitors actively and closely through GPS
          tracker, user's car is covered by car insurance, and more!
        </p>
      </div>
    </div>
    </div>
  );
}

export default About;