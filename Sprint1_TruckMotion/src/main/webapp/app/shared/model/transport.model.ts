import dayjs from 'dayjs';
import { ILocation } from 'app/shared/model/location.model';
import { IDriver } from 'app/shared/model/driver.model';
import { IServiceRequest } from 'app/shared/model/service-request.model';

export interface ITransport {
  id?: string;
  startTime?: dayjs.Dayjs | null;
  endTime?: dayjs.Dayjs | null;
  location?: ILocation | null;
  driver?: IDriver | null;
  serviceRequest?: IServiceRequest | null;
}

export const defaultValue: Readonly<ITransport> = {};
