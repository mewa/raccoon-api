variable "region" {
  default = "europe-west1"
}

variable "zone" {
  default = "europe-west1-b"
}

variable "k8s_version" {
  default = "1.9.7-gke.0"
}

variable "project" {
  default = "kubetest-202411"
}

variable "cluster_name" {
  default = "kubetest-cluster-1"
}

provider "google" {
  credentials = "${file("credentials.json")}"
  project     = "${var.project}"
  region      = "${var.region}"
}

resource "google_container_cluster" "raccoon_cluster" {
  name = "${var.cluster_name}"
  zone = "${var.zone}"

  min_master_version =  "${var.k8s_version}"
  node_version = "${var.k8s_version}"

  master_auth {
    password = ""
    username = ""
  }

  node_pool {
    name = "default-pool"
  }

  remove_default_node_pool = true
}

resource "google_container_node_pool" "raccoon_nodes" {
  name = "${var.cluster_name}-pool"
  zone = "${var.zone}"
  cluster = "${google_container_cluster.raccoon_cluster.id}"

  initial_node_count = 2

  autoscaling {
    min_node_count = 3
    max_node_count = 5
  }

  management {
    auto_repair = true
    auto_upgrade = true
  }

  node_config {
    disk_size_gb = 32
    preemptible = true
  }
}
