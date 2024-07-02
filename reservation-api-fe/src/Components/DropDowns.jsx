import Dropdown from 'react-bootstrap/Dropdown';

function DropDowns() {
  return (
    <Dropdown>
      <Dropdown.Toggle variant="success" id="dropdown-basic">
        Accounts
      </Dropdown.Toggle>

      <Dropdown.Menu>
        <Dropdown.Item href="/adminhomepage/addbus">Add Bus</Dropdown.Item>
        <Dropdown.Item href="#/action-2">Buses List</Dropdown.Item>
        <Dropdown.Item href="#/action-2">Edit Profile</Dropdown.Item>
        <Dropdown.Item href="#/action-3">LogOut</Dropdown.Item>
      </Dropdown.Menu>
    </Dropdown>
  );
}

export default DropDowns;