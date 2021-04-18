FROM adoptopenjdk:11-jdk-openj9-bionic AS builder

COPY --from=builder /opt/jre-minimal /opt/jre-minimal
ENV PATH="$PATH:/opt/jre-minimal/bin"

ARG VERSION_NO

COPY target/lib /deps

COPY target/vending.machine-${VERSION_NO}.jar app.jar

RUN chown -R user:user /deps
RUN chown user:user app.jar

USER user
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-cp", "app.jar:deps/*", "de.perdata.vending.machine.Main"]
