name := "S99"

scalaVersion := dottyLatestNightlyBuild.get

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.0" % "test",
  "org.scalacheck" %% "scalacheck" % "1.14.3" % "test"
).map(_ withDottyCompat scalaVersion.value)

val unusedWarnings = Def.setting(
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, 11)) =>
      List("-Ywarn-unused-import")
    case _ =>
      List("-Ywarn-unused:imports")
  }
)

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
)

scalacOptions ++= {
  val common = "-language:existentials,higherKinds,implicitConversions"
  if (isDotty.value) {
    Seq(
      common + ",Scala2Compat",
    )
  } else {
    (unusedWarnings.value: @sbtUnchecked) ++ Seq(
      common,
      "-Xlint",
    )
  }
}

scalacOptions ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, 11 | 12)) =>
      Seq("-Yno-adapted-args", "-Xfuture")
    case _ =>
      Nil
  }
}

Seq(Compile, Test).flatMap(c =>
  scalacOptions in (c, console) --= unusedWarnings.value
)
