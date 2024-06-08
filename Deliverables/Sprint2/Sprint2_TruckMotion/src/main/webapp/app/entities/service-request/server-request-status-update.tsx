import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat, ValidatedField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity, updateEntityStatus } from './service-request.reducer';
import { fetchFreeDrivers } from '../driver/driver.reducer';


interface IAvailableDriverDTO {
    id: string;
    name: string;
  }

export const ServiceRequestStatusUpdate = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  const navigate = useNavigate();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const updateStatus = (id: string, isApproved: boolean, driverId : string, startDate : string , endDate : string) => {
    dispatch(updateEntityStatus({ id, isApproved, driverId, startDate, endDate }));
    navigate('/service-request');
  };

  const [selectedDriver, setSelectedDriver] = useState(null);

  const [selectedStartDate, setSelectedStartDate] = useState(null);

  const [selectedEndDate, setSelectedEndDate] = useState(null);

  const [freeDrivers, setFreeDrivers] = useState<IAvailableDriverDTO[]>([]);

  useEffect(() => {
    if (selectedStartDate && selectedEndDate) {
      dispatch(fetchFreeDrivers({ startDate: selectedStartDate, endDate: selectedEndDate }))
        .then((response: any) => {
          setFreeDrivers(response.payload);
        })
        .catch((error: any) => {
            setFreeDrivers([])
        });
    }
  }, [dispatch, selectedStartDate, selectedEndDate]);


  const handleChangeDriver = (event) => {
    setSelectedDriver(event.target.value);
  };

  const handleChangeStartDate = (event) => {
    setSelectedStartDate(event.target.value);
  };

  const handleChangeEndDate = (event) => {
    setSelectedEndDate(event.target.value);
  };


  const serviceRequestEntity = useAppSelector(state => state.serviceRequest.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="serviceRequestDetailsHeading">Service Request Approval</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="items">Items</span>
          </dt>
          <dd>{serviceRequestEntity.items}</dd>
          <dt>
            <span id="serviceName">Service Name</span>
          </dt>
          <dd>{serviceRequestEntity.serviceName}</dd>
          <dt>
            <span id="totalWeightOfItems">Total Weight Of Items</span>
          </dt>
          <dd>{serviceRequestEntity.totalWeightOfItems}</dd>
          <dt>
            <span id="price">Price</span>
          </dt>
          <dd>{serviceRequestEntity.price}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>
            {serviceRequestEntity.date ? <TextFormat value={serviceRequestEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>Location Coordinates(x,y,z)</dt>
          <dd>{serviceRequestEntity.location ?  serviceRequestEntity.location.coordX +"--" +serviceRequestEntity.location.coordY+"--" +serviceRequestEntity.location.coordZ : ''}</dd>
          <dt>Customer Company</dt>
          <dd>{serviceRequestEntity.customer ? serviceRequestEntity.customer.company : ''}</dd>
          <dt>Select Driver and Dates</dt>
          {freeDrivers?.length > 0 ? <ValidatedField 
        id="transport-driver" 
        name="driver" 
        data-cy="driver" 
        label="Driver" 
        type="select"
        onChange={handleChangeDriver}
      >
        <option value="" key="0" />
        {freeDrivers
          ? freeDrivers.map(otherEntity => (
              <option value={otherEntity.id} key={otherEntity.id}>
                {otherEntity.name}
              </option>
            ))
          : null}
      </ValidatedField> : null} 
         
      <ValidatedField
                onChange={handleChangeStartDate}
                label="Start Date"
                id="service-request-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
                     <ValidatedField
                     onChange={handleChangeEndDate}
                label="Delivered Date"
                id="service-request-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />       




        </dl>
        <Button tag={Link} to="/service-request" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        {selectedDriver && selectedStartDate && selectedEndDate ? <Button onClick={() =>  updateStatus(serviceRequestEntity.id, true, selectedDriver, selectedStartDate, selectedEndDate)} replace color="primary">
          <FontAwesomeIcon icon="plus" /> <span className="d-none d-md-inline">Approve</span>
        </Button>: null}
        
      </Col>
    </Row>
  );
};

export default ServiceRequestStatusUpdate;
