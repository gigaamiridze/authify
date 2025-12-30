export interface ErrorInfo {
  status: number;
  error: string;
  message: string;
  path: string;
  timestamp: string;
  errorCode: string;
  arguments: Map<string, string>;
}