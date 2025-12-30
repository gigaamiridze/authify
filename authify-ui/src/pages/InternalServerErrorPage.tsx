import { ErrorResult } from '../components';

function InternalServerErrorPage() {
  return (
    <ErrorResult
      status='500'
      title='500'
      subTitle='Sorry, something went wrong.'
    />
  );
}

export default InternalServerErrorPage;