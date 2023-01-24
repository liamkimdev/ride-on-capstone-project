import { useEffect, useState } from "react";
import { Route, Routes, Navigate } from "react-router-dom";
import jwtDecode from "jwt-decode";
import Login from "./Utilities/Login";
import Register from "./Utilities/Register";
import AuthContext from "./contexts/AuthContext";
import Home from "./components/Home";
import Nav from "./Utilities/Nav";
import About from "./components/About";
import TripForm from "./components/TripForm";
import RideOn from "./components/RideOn";
import CarForm from "./components/CarForm";
import "./styles/rideOn.css";
import TripFactory from "./components/TripFactory";

const LOCAL_STORAGE_TOKEN_KEY = "rideOnToken";

function App() {
  const [messages, setMessages] = useState([]);
  const [currentUser, setCurrentUser] = useState(null);
  const [cars, setCars] = useState([]);
  const [trips, setTrips] = useState([]);

  useEffect(() => {
    const token = localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY);

    if (token) {
      login(token);
    }
  }, []);

  const login = (token) => {
    const { sub: username, authorities: authoritiesString, user_id: userId } = jwtDecode(token);

    localStorage.setItem(LOCAL_STORAGE_TOKEN_KEY, token);

    let cars = [];

    fetch(`http://localhost:8080/api/ride_on/user/${username}`, {
      headers: {
        Authorization: "Bearer " + token
      }
    })
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else if (response.status === 403) {
          // setMessages([
          //   ...messages,
          //   {
          //     id: makeId(),
          //     type: "failure",
          //     text: "Car could not be registered.",
          //   },
          // ]);
        } else {
          // setMessages([
          //     ...messages,
          //     {
          //       id: makeId(),
          //       type: "failure",
          //       text: "Unexpected error occured.",
          //     },
          //   ]);
        };
      })
      .then((data) => {
        console.log(data);
        cars = data.cars;

        const roles = authoritiesString.split(",");

        const user = {
          username,
          userId,
          roles,
          token,
          cars,
          hasRole(role) {
            return this.roles.includes(role);
          },
        };

        
        setCurrentUser(user);
        
        console.log(currentUser.cars[0].carId);

        return user;

      })
      .catch((error) => console.log(error));
  };

  const logout = () => {
    setCurrentUser(null);
    localStorage.removeItem(LOCAL_STORAGE_TOKEN_KEY);
  };

  const auth = {
    currentUser: currentUser ? { ...currentUser } : null,
    login,
    logout,
  };

  const isPasswordComplex = (password) => {
    let digits = 0;
    let letters = 0;
    let others = 0;

    const characters = [...password];

    for (let c of characters) {
      const charCode = c.charCodeAt(0);

      if (charCode >= 48 && charCode <= 57) {
        // numbers 0-9
        digits++;
      } else if (
        (charCode >= 65 && charCode <= 90) ||
        (charCode >= 97 && charCode <= 122)
      ) {
        // lowercase and uppercase letters
        letters++;
      } else {
        others++;
      }
    }

    return digits > 0 && letters > 0 && others > 0;
  };

  const makeId = () => {
    let id = "";
    let characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    for (var i = 0; i < 12; i++) {
      id += characters.charAt(Math.floor(Math.random() * 36));
    }
    return id;
  };

  return (
    <AuthContext.Provider value={auth}>
      <div className="container">
        <Nav />
        <Routes>
          {/* If logged in, go to form page, if not go to home page
          <Route path="/edit/:id" element={
            currentUser ? <SightingForm /> : <Navigate to="/" />
          }/>
          
          
          <Route path="/add" element={
            currentUser ? <SightingForm /> : <Navigate to="/" />
          }/> */}

          {/* If logged in, go to home page, if not go to login page */}
          <Route
            path="/login"
            element={
              currentUser ?
                <Navigate to="/home" />
                :
                <Login
                  messages={messages}
                  setMessages={setMessages}
                  makeId={makeId}
                  isPasswordComplex={isPasswordComplex}
                />

            }
          />

          {/* If logged in, go to home page, if not go to register page */}
          <Route
            path="/register"
            element={
              currentUser ?
                <Navigate to="/home" />
                :
                <Register
                  messages={messages}
                  setMessages={setMessages}
                  makeId={makeId}
                  isPasswordComplex={isPasswordComplex}
                />

            }
          />

          <Route path="/about" element={<About />} />
          {/* // <Route path="*" element={<NotFound />}/>  */}

          <Route path="/home" element=
            {<Home
              currentUser={currentUser}
              cars={cars}
              setCars={setCars} />} />

          <Route path="/" element={<RideOn />} />

          <Route path="/api/ride_on/trip/form" element={
            //currentUser && currentUser.hasRole("DRIVER") ?
            <TripForm 
            currentUser={currentUser}
            setCurrentUser={setCurrentUser}
            />
            // messages={messages} 
            //   setMessages={setMessages} 
            //   makeId={makeId}  
            // /> : <Navigate to="/api/ride_on/car/form" />
          } />

          <Route path="/api/ride_on/trip" element={
            <TripFactory
              trips={trips}
              setTrips={setTrips} />
          } />

          <Route path="/api/ride_on/car/form" element={<CarForm />} />

          {/* // <Route path="*" element={<NotFound />}/>  */}
        </Routes>
      </div>
    </AuthContext.Provider>
  );
}

export default App;
