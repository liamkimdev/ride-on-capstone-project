import { useContext } from "react";
import { Link, NavLink } from "react-router-dom";
import AuthContext from "../contexts/AuthContext";

function Nav() {
  const auth = useContext(AuthContext);

  return (
    <nav className="navbar navbar-expand-md navbar-light bg-nav fixed-top mb-5 text">
      <div className="container-fluid d-block px-5">
        <div className="row">
          <div className="col align-self-center">
            <NavLink
              to="/transport"
              className={({ isActive }) =>
                isActive ? "nav-link active" : "nav-link"
              }
            >
              <span className="emoji">👍</span> Let's Ride
            </NavLink>
          </div>
          <div className="col text-center align-self-center">
            <Link className="navbar-brand" to="/"
              style={{ fontFamily: 'cutestThings', color: '#3CB2FB', fontSize: '45px' }}>
              RIDE ON
            </Link>
          </div>
          <div className="col text-end align-self-center">
            <button
              className="navbar-toggler"
              type="button"
              data-toggle="collapse"
              data-target="#navbarNavDropdown"
              aria-controls="navbarNavDropdown"
              aria-expanded="false"
              aria-label="Toggle navigation"
            >
              <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse" id="navbarNavDropdown">
              <ul className="navbar-nav ms-auto">
                <li className="nav-item">
                  <NavLink
                    to="/about"
                    className={({ isActive }) =>
                      isActive ? "nav-link active" : "nav-link"
                    }
                  ><span className="emoji">📝</span> About
                  </NavLink>
                </li>
                {!auth.currentUser ? (
                  <>
                    <li className="nav-item">
                      <NavLink
                        to="/signin"
                        className={({ isActive }) =>
                          isActive ? "nav-link active" : "nav-link"
                        }
                      >
                        <span className="emoji">🚪</span> Sign In
                      </NavLink>
                    </li>
                  </>
                ) : null}
                {auth.currentUser ? (
                  <li>
                    <Link className="nav-link" onClick={auth.logout}>
                      <NavLink to="/" className="text-reset" style={{ textDecoration: "none" }}>
                        <span className="emoji">🚪</span> Sign Out
                      </NavLink>
                    </Link>
                  </li>
                ) : null}
              </ul>
            </div>
          </div>
        </div>
      </div>
    </nav>
  );
}
export default Nav;
