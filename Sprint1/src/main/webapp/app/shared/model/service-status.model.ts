import dayjs from 'dayjs';
import { IService } from 'app/shared/model/service.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IServiceStatus {
  id?: string;
  date?: dayjs.Dayjs | null;
  observations?: string | null;
  status?: keyof typeof Status | null;
  service?: IService | null;
}

export const defaultValue: Readonly<IServiceStatus> = {};
