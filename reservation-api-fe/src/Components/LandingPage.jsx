import { Link } from "react-router-dom";
import '../Styles/LandingPage.css'

const LandingPage = () => {
    return ( 
        <div className="landingpage">
            <Link to="/adminlogin">
                <img src="https://static.vecteezy.com/system/resources/thumbnails/008/302/513/small/eps10-blue-user-icon-or-logo-in-simple-flat-trendy-modern-style-isolated-on-white-background-free-vector.jpg" alt="img" />
                <h2>Admin</h2>
            </Link>
            <Link to="/userlogin">
                <img src="https://cdn-icons-png.flaticon.com/512/9187/9187604.png" alt="img" />
                <h2>User</h2>
            </Link>
        </div>
     );
}
 
export default LandingPage;