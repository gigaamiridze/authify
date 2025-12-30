FROM node:22-alpine AS build-authify-ui

WORKDIR /build/authify-ui

RUN corepack enable

COPY package.json yarn.lock ./

RUN corepack prepare yarn@stable --activate
RUN yarn config set nodeLinker node-modules
RUN yarn install --immutable

COPY . .

RUN yarn build

FROM nginx:1.28.0-alpine

ARG DIST_FOLDER=/build/authify-ui/dist

COPY --from=build-authify-ui ${DIST_FOLDER} /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

ENTRYPOINT ["nginx"]
CMD ["-g", "daemon off;"]