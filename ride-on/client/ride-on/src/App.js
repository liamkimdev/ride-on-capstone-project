import { useEffect, useState } from "react";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";
import jwtDecode from "jwt-decode";
import Confirmation from "./components/Confirmation";
import Error from "./components/Error";
import Login from "./components/Login";
import Register from "./components/Register";
import AuthContext from "./contexts/AuthContext";
import Home from "./components/Home";
import Nav from "./components/Nav";
import About from "./components/About";
import TripForm from "./components/TripForm";
import RideOn from "./components/RideOn";
import CarForm from "./components/CarForm";

const LOCAL_STORAGE_TOKEN_KEY = "rideOnToken";

function App() {
  const [currentUser, setCurrentUser] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY);

    if (token) {
      login(token);
    }
  }, []);

  const login = (token) => {
    const { sub: username, authorities: authoritiesString } = jwtDecode(token);

    const roles = authoritiesString.split(",");

    const user = {
      username,
      roles,
      token,
      hasRole(role) {
        return this.roles.includes(role);
      },
    };

    console.log(user);

    setCurrentUser(user);

    return user;
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

  return (
    <AuthContext.Provider value={auth}>
      <Nav />
      <div>
        <Routes>
          {/* If logged in, go to form page, if not go to home page
          <Route path="/edit/:id" element={
            currentUser ? <SightingForm /> : <Navigate to="/" />
          }/>
          
          
          <Route path="/add" element={
            currentUser ? <SightingForm /> : <Navigate to="/" />
          }/> */}

          <Route path="/confirmation" element={<Confirmation />} />
          <Route path="/error" element={<Error />} />

          {/* If logged in, go to home page, if not go to login page */}
          <Route
            path="/login"
            element={currentUser ? <Navigate to="/" /> : <Login />}
          />

          {/* If logged in, go to home page, if not go to register page */}
          <Route
            path="/register"
            element={currentUser ? <Navigate to="/" /> : <Register />}
          />

           <Route path="/about" element={<About />} />
          {/* // <Route path="*" element={<NotFound />}/>  */}

          <Route path="/home" element={<Home />} />
          
          <Route path="/" element={<RideOn />} />
          
          <Route path="/api/ride_on/trip/form" element={<TripForm />} />

          <Route path="/api/ride_on/car/form" element={<CarForm />} />

          {/* // <Route path="*" element={<NotFound />}/>  */}
        </Routes>
      </div>
    </AuthContext.Provider>
  );
}

export default App;
