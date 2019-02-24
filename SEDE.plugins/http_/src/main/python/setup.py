import setuptools

dependencies = [
      'gunicorn',
      'flask'
]

setuptools.setup(name='SEDE-http-executor',
      version='0.0.3',
      description='SEDE python  http executor',
      url='http://github.com/fmohr/SEDE',
      author='Amin Faez',
      author_email='aminfaez@mail.upb.de',
      license='GNU',
      long_description_content_type="text/markdown",
      packages=setuptools.find_packages(),
      install_requires=dependencies,
      entry_points=
            {
                  'console_scripts': [
                        'pyexecutor = flask_integration:main'
                  ]
            })