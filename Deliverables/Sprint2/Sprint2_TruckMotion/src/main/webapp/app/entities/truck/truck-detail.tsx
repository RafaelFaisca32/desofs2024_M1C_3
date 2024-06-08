import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './truck.reducer';
import { AUTHORITIES } from '../../config/constants';

export const TruckDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  
  const account = useAppSelector(state => state.authentication.account);
  const managerRole = [AUTHORITIES.MANAGER];
  const authorities = account.authorities as string[]

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const truckEntity = useAppSelector(state => state.truck.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="truckDetailsHeading">Truck</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="make">Make</span>
          </dt>
          <dd>{truckEntity.make}</dd>
          <dt>
            <span id="model">Model</span>
          </dt>
          <dd>{truckEntity.model}</dd>
        </dl>
        <Button tag={Link} to="/truck" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        {managerRole.some(value => authorities.includes(value))  ?
        <Button tag={Link} to={`/truck/${truckEntity.id}/edit`} replace color="primary">
        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
         : null}

      </Col>
    </Row>
  );
};

export default TruckDetail;
