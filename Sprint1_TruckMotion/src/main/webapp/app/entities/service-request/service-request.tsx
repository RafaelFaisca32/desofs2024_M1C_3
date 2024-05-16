import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './service-request.reducer';

export const ServiceRequest = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const serviceRequestList = useAppSelector(state => state.serviceRequest.entities);
  const loading = useAppSelector(state => state.serviceRequest.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="service-request-heading" data-cy="ServiceRequestHeading">
        Service Requests
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/service-request/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Service Request
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {serviceRequestList && serviceRequestList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  Service Request Number <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('items')}>
                  Items <FontAwesomeIcon icon={getSortIconByFieldName('items')} />
                </th>
                <th className="hand" onClick={sort('serviceName')}>
                  Service Name <FontAwesomeIcon icon={getSortIconByFieldName('serviceName')} />
                </th>
                <th className="hand" onClick={sort('totalWeightOfItems')}>
                  Total Weight Of Items <FontAwesomeIcon icon={getSortIconByFieldName('totalWeightOfItems')} />
                </th>
                <th className="hand" onClick={sort('price')}>
                  Price <FontAwesomeIcon icon={getSortIconByFieldName('price')} />
                </th>
                <th className="hand" onClick={sort('date')}>
                  Date <FontAwesomeIcon icon={getSortIconByFieldName('date')} />
                </th>
                <th>
                Location Coordinates(x,y,z) <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Customer Company <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Status <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {serviceRequestList.map((serviceRequest, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/service-request/${serviceRequest.id}`} color="link" size="sm">
                      {i}
                    </Button>
                  </td>
                  <td>{serviceRequest.items}</td>
                  <td>{serviceRequest.serviceName}</td>
                  <td>{serviceRequest.totalWeightOfItems}</td>
                  <td>{serviceRequest.price}</td>
                  <td>{serviceRequest.date ? <TextFormat type="date" value={serviceRequest.date} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {serviceRequest.location ? (
                      <Link to={`/location/${serviceRequest.location.id}`}>{serviceRequest.location.coordX +"--" +serviceRequest.location.coordY+"--" +serviceRequest.location.coordZ}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {serviceRequest.customer ? (
                      <Link to={`/customer/${serviceRequest.customer.id}`}>{serviceRequest.customer.company}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {serviceRequest.status ? (
                      serviceRequest.status.status
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/service-request/${serviceRequest.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/service-request/${serviceRequest.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/service-request/${serviceRequest.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Service Requests found</div>
        )}
      </div>
    </div>
  );
};

export default ServiceRequest;
