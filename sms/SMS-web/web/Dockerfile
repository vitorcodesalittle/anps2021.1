FROM hseeberger/scala-sbt:8u265_1.4.3_2.13.4 as build
MAINTAINER vlma@cin.ufpe.br

WORKDIR /app
COPY . .
RUN ls -al
RUN sbt dist
WORKDIR /app/target/universal/

FROM hseeberger/scala-sbt:8u265_1.4.3_2.13.4
WORKDIR /web
COPY --from=build /app/target/universal/web-1.0-SNAPSHOT.zip .
RUN unzip web-1.0-SNAPSHOT.zip

EXPOSE 9000
CMD /web/web-1.0-SNAPSHOT/bin/web
