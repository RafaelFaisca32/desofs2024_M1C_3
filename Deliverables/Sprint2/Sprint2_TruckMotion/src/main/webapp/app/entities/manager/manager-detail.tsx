import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './manager.reducer';

export const ManagerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const managerEntity = useAppSelector(state => state.manager.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="managerDetailsHeading">Manager</h2>
        <dl className="jh-entity-details">
          <dt>Application User</dt>
          <dd>{managerEntity.applicationUser ? managerEntity.applicationUser.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/manager" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
      </Col>
    </Row>
  );
};

export default ManagerDetail;
