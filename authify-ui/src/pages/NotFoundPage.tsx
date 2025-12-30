import { ErrorResult } from '../components';

function NotFoundPage() {
  return (
    <ErrorResult
      status='404'
      title='404'
      subTitle='Sorry, the page you visited does not exist.'
    />
  );
}

export default NotFoundPage;