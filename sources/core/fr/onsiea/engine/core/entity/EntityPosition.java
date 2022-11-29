package fr.onsiea.engine.core.entity;

import org.joml.Vector3f;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
public class EntityPosition
{
	private Vector3f	position;
	private Vector3f	orientation;
	private Vector3f	scale;
}