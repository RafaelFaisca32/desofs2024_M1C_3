import './home.scss';
import './delivery.scss';

import React from 'react';
import { Link } from 'react-router-dom';

import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="3" className="pad">
        <span className="hipster rounded" />
      </Col>
      <Col md="9">
        <h1 className="display-4">Welcome to TruckMotion!</h1>
        {account?.login ? (
          <div>
            <Alert color="success">You are logged in as user &quot;{account.login}&quot;.</Alert>
          </div>
        ) : (
          <div>
            <Alert color="warning">
              If you want to sign in 
              <span>&nbsp;</span>
              <Link to="/login" className="alert-link">
                click here
              </Link>
              .
            </Alert>

            <Alert color="warning">
              You don&apos;t have an account yet?&nbsp;

                Contact your manager and try to sign in later.

            </Alert>

            <Col md="6" className="pad">
        <span className="delivery rounded" />
      </Col>
          </div>
        )}
       
      </Col>
    </Row>
  );
};

export default Home;
