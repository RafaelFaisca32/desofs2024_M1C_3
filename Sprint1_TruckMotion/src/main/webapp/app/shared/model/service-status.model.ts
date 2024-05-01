import dayjs from 'dayjs';
import { IServiceRequest } from 'app/shared/model/service-request.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IServiceStatus {
  id?: string;
  date?: dayjs.Dayjs | null;
  observations?: string | null;
  status?: keyof typeof Status | null;
  serviceRequest?: IServiceRequest | null;
}

export const defaultValue: Readonly<IServiceStatus> = {};
