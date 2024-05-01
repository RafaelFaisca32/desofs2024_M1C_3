import dayjs from 'dayjs';
import { ILocation } from 'app/shared/model/location.model';
import { IDriver } from 'app/shared/model/driver.model';
import { IService } from 'app/shared/model/service.model';

export interface ITransport {
  id?: string;
  startTime?: dayjs.Dayjs | null;
  endTime?: dayjs.Dayjs | null;
  location?: ILocation | null;
  driver?: IDriver | null;
  service?: IService | null;
}

export const defaultValue: Readonly<ITransport> = {};
