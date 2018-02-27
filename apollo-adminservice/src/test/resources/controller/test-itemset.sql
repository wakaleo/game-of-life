INSERT INTO App (AppId, Name, OwnerName, OwnerEmail) VALUES ('someAppId','someAppName','someOwnerName','someOwnerName@ctrip.com');

INSERT INTO Cluster (AppId, Name) VALUES ('someAppId', 'default');

INSERT INTO AppNamespace (AppId, Name) VALUES ('someAppId', 'application');

INSERT INTO Namespace (AppId, ClusterName, NamespaceName) VALUES ('someAppId', 'default', 'application');
