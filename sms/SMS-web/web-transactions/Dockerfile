FROM hseeberger/scala-sbt:8u265_1.4.3_2.13.4 as build
MAINTAINER vlma@cin.ufpe.br

WORKDIR /app
COPY . .
RUN sbt update
RUN sbt dist
WORKDIR /app/target/universal/

FROM hseeberger/scala-sbt:8u265_1.4.3_2.13.4
WORKDIR /web
COPY --from=build /app/target/universal/transaction-1.0-SNAPSHOT.zip .
RUN unzip transaction-1.0-SNAPSHOT.zip

EXPOSE 9000
CMD /web/transaction-1.0-SNAPSHOT/bin/transaction
