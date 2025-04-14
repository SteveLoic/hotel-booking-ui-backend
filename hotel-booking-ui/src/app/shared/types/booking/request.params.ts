export interface RequestParamsInteface {
  page: number;
  size: number;
}

export const defaultRequestParams: RequestParamsInteface = {
  page: 0,
  size: 10,
};
