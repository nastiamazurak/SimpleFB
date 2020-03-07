import React from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Link} from 'react-router-dom';
import Dropdown from "react-bootstrap/Dropdown";
import NavItem from "react-bootstrap/NavItem";
import NavLink from "react-bootstrap/NavLink";


export class AuthHeader extends React.Component {

    render() {
        return (
            <Dropdown as={NavItem}>
                <Dropdown.Toggle as={NavLink}>{this.props.username}</Dropdown.Toggle>
                <Dropdown.Menu className="dropdown-menu-right">
                    <Dropdown.Item as={Link} to="/user">My profile</Dropdown.Item>
                    <Dropdown.Divider/>
                </Dropdown.Menu>
            </Dropdown>

        );
    }
}
export default AuthHeader;