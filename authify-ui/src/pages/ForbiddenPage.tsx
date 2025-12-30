import { ErrorResult } from '../components';

function ForbiddenPage() {
  return (
    <ErrorResult
      status='403'
      title='403'
      subTitle='Sorry, you are not authorized to access this page.'
    />
  );
}

export default ForbiddenPage;