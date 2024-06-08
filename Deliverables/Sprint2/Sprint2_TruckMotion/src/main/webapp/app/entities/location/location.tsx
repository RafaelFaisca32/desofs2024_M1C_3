import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './location.reducer';
import { AUTHORITIES } from '../../config/constants';

export const Location = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const account = useAppSelector(state => state.authentication.account);
  const customerRole = [AUTHORITIES.CUSTOMER];
  const authorities = account.authorities as string[]

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const locationList = useAppSelector(state => state.location.entities);
  const loading = useAppSelector(state => state.location.loading);

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
      <h2 id="location-heading" data-cy="LocationHeading">
        Locations
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          {
            customerRole.some(value => authorities.includes(value)) ? 
            <Link to="/location/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Location
          </Link>
            :null
          }

        </div>
      </h2>
      <div className="table-responsive">
        {locationList && locationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th >
                  Location Number <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('coordX')}>
                  Coord X <FontAwesomeIcon icon={getSortIconByFieldName('coordX')} />
                </th>
                <th className="hand" onClick={sort('coordY')}>
                  Coord Y <FontAwesomeIcon icon={getSortIconByFieldName('coordY')} />
                </th>
                <th className="hand" onClick={sort('coordZ')}>
                  Coord Z <FontAwesomeIcon icon={getSortIconByFieldName('coordZ')} />
                </th>
                <th>
                  Customer Company <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {locationList.map((location, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/location/${location.id}`} color="link" size="sm">
                      {i}
                    </Button>
                  </td>
                  <td>{location.coordX}</td>
                  <td>{location.coordY}</td>
                  <td>{location.coordZ}</td>
                  <td>{location.customer ? <Link to={`/customer/${location.customer.id}`}>{location.customer.company}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/location/${location.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>

                        {
                      customerRole.some(value => authorities.includes(value)) ? 
                      <><Button tag={Link} to={`/location/${location.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                          </Button><Button
                            onClick={() => (window.location.href = `/location/${location.id}/delete`)}
                            color="danger"
                            size="sm"
                            data-cy="entityDeleteButton"
                          >
                              <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                            </Button></>
                          : null
                        }

                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Locations found</div>
        )}
      </div>
    </div>
  );
};

export default Location;
