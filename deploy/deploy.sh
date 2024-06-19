# /bin/bash

# git仓库地址
REPOSITORY_URL=https://github.com/GuDaoCoder/blog.git
# 分支名称
BRANCH_NAME=master
# 代码下载目录
DESTINATION_DIR=/home/deploy/git
# 后端工程目录
BACKEND_DIR=$DESTINATION_DIR/blog-backend
# 后端镜像打包目录
BACKEND_BUILD_DIR=$DESTINATION_DIR/build/$BACKEND_DIR
# 镜像名称
IMAGE_NAME=backend-app
# 后端容器名称
BACKEND_CONTAINER_NAME=backend-app-container
# 环境变量文件
ENV_FILE=/root/env.list
#前端工程目录
FRONT_DIR=$DESTINATION_DIR/blog-front
#前端容器名称
FRONT_CONTAINER_NAME=front-app-container
#nginx配置文件
NGINX_CONF_FILE=$DESTINATION_DIR/deploy/docker/nginx/conf

# 如果目标目录不存在，则创建
if [ ! -d "$DESTINATION_DIR" ]; then
    mkdir -p "$DESTINATION_DIR"
fi

# 进入目标目录
cd "$DESTINATION_DIR" || { echo "进入目标目录失败: $DESTINATION_DIR"; exit 1; }

# 如果目录已经是一个git仓库，拉取最新代码；否则克隆仓库
if [ -d ".git" ]; then
    echo "目标目录已经是Git仓库，正在拉取最新代码"
    git fetch origin
    git checkout "$BRANCH_NAME"
    git pull origin "$BRANCH_NAME"
else
    echo "正在拉取最新代码"
    git clone --branch "$BRANCH_NAME" "$REPOSITORY_URL" .
fi

echo "代码已下载至$DESTINATION_DIR"

# 打包backend工程
echo "开始打包后端工程"

# 进入backend目录并打包
cd "$BACKEND_DIR" || { echo "Backend directory not found!"; exit 1; }
mvn clean install || { echo "Maven build failed!"; exit 1; }

echo "后端工程打包成功"

# 查找target目录下的JAR包并复制到build目录
JAR_FILE=$(find target -name "*.jar" | head -n 1)
if [ -z "$JAR_FILE" ]; then
    echo "目标目录未找到JAR文件"
    exit 1
fi

mkdir -p $BACKEND_BUILD_DIR

# 如果有同名容器正在运行，先停止并删除
if [ "$(docker ps -a -q -f name=$BACKEND_CONTAINER_NAME)" ]; then
    echo "停止并删除正在运行的容器: $BACKEND_CONTAINER_NAME"
    docker stop $BACKEND_CONTAINER_NAME
    docker rm $BACKEND_CONTAINER_NAME
fi

# 如果有同名镜像，先删除
if [ "$(docker images -q $IMAGE_NAME)" ]; then
    echo "删除存在的镜像: $IMAGE_NAME"
    docker rmi $IMAGE_NAME
fi

echo "开始打包后端容器镜像"

# 复制JAR包到build目录
cp "$JAR_FILE" "$BACKEND_BUILD_DIR/" || { echo "复制JAR包到build目录失败"; exit 1; }
# 复制Dockerfile文件
cp "$BACKEND_DIR/Dockerfile" "$BACKEND_BUILD_DIR/" || { echo "复制Dockerfile文件到build目录失败"; exit 1; }

# 进入包含Dockerfile的build目录
cd "$BACKEND_BUILD_DIR" || { echo "进入目标目录失败：$BACKEND_BUILD_DIR"; exit 1; }

# 构建Docker镜像
docker build -t $IMAGE_NAME .

echo "后端容器镜像打包成功"

# 运行Docker容器
docker run -d --name $BACKEND_CONTAINER_NAME -p 8173:8173 --env-file $ENV_FILE -e TZ=Asia/Shanghai --network host $IMAGE_NAME

echo "Docker容器 $BACKEND_CONTAINER_NAME 启动成功"

echo "开始打包前端工程"

# 进入前端工程
cd "$FRONT_DIR" || { echo "进入目标目录失败：$FRONT_DIR"; exit 1; }

# 打包
npm install
npm run build-only

echo "前端工程打包成功"

# 创建挂载路径
mkdir -p /home/docker/nginx/html
rm -rf /home/docker/nginx/html/*
mkdir -p /home/docker/nginx/conf
mkdir -p /home/docker/nginx/logs

# 复制文件
cp -r dist/* /home/docker/nginx/html
cp -r "$NGINX_CONF_FILE/default.conf" /home/docker/nginx/conf

# 如果有同名容器正在运行，先停止并删除
if [ "$(docker ps -a -q -f name=$FRONT_CONTAINER_NAME)" ]; then
    echo "停止并删除正在运行的容器: $FRONT_CONTAINER_NAME"
    docker stop $FRONT_CONTAINER_NAME
    docker rm $FRONT_CONTAINER_NAME
fi

docker run -d --name $FRONT_CONTAINER_NAME \
          -p 80:80 -p 443:443 \
          -e TZ=Asia/Shanghai \
          -v /home/docker/nginx/conf/default.conf:/etc/nginx/conf.d/default.conf \
          -v /home/docker/nginx/html:/usr/share/nginx/html \
          -v /home/docker/nginx/logs:/var/log/nginx \
          --network host \
          nginx:latest

echo "Docker容器 $FRONT_CONTAINER_NAME 启动成功"